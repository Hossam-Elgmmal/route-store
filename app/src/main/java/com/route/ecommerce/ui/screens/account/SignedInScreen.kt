package com.route.ecommerce.ui.screens.account

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.route.datastore.UserData
import com.route.ecommerce.R
import com.route.ecommerce.ui.EcomAppState
import com.route.ecommerce.ui.components.EcomErrorDialog
import com.route.ecommerce.ui.components.LoadingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@Composable
fun SignedInScreen(
    coroutineScope: CoroutineScope,
    viewModel: AccountViewModel,
    userData: UserData,
    appState: EcomAppState,
    modifier: Modifier = Modifier,
) {
    var showSignOutDialog by remember { mutableStateOf(false) }
    var showEditInfoCard by remember { mutableStateOf(false) }
    var showEditPasswordCard by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            coroutineScope.launch(Dispatchers.IO) {
                if (uri != null) {
                    try {
                        viewModel.setUserImgName("0")
                        val imageName = "user_image.jpg"
                        context.contentResolver.openInputStream(uri)
                            ?.use { inputStream ->
                                val bitmap =
                                    BitmapFactory.decodeStream(inputStream)
                                val file = File(context.filesDir, imageName)

                                FileOutputStream(file).use {
                                    bitmap.compress(
                                        Bitmap.CompressFormat.JPEG,
                                        100,
                                        it
                                    )
                                }
                                viewModel.setUserImgName(file.path)
                            }
                    } catch (e: Exception) {
                        Log.d("saving image error", "uri: ${e.message}")
                    }
                }
            }
        }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = userData.userImgPath.ifEmpty {
                R.drawable.ic_person
            },
            contentDescription = stringResource(R.string.user_image),
            modifier = Modifier
                .padding(top = 8.dp, bottom = 16.dp)
                .size(160.dp)
                .shadow(1.dp, CircleShape)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onSurfaceVariant, CircleShape)
                .clickable {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop
        )

        AnimatedVisibility(
            visible = showEditInfoCard
        ) {
            EditInfoCard(
                onCancel = {
                    showEditInfoCard = false
                    viewModel.cancelEditInfo()
                },
                token = userData.userToken,
                viewModel = viewModel,
            )
        }
        AnimatedVisibility(
            visible = showEditPasswordCard
        ) {
            EditPasswordCard(
                onCancel = {
                    showEditPasswordCard = false
                    viewModel.cancelEditPassword()
                },
                token = userData.userToken,
                password = userData.userPassword,
                viewModel = viewModel
            )
        }
        AnimatedVisibility(
            visible = !showEditInfoCard && !showEditPasswordCard
        ) {
            InfoCard(
                userData = userData,
                onEditInfoClick = { showEditInfoCard = true },
                onEditPasswordClick = { showEditPasswordCard = true }
            )
        }

        ElevatedButton(
            onClick = { appState.navigateToOrders() },
            shape = MaterialTheme.shapes.extraSmall,
            colors = ButtonDefaults
                .elevatedButtonColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_orders),
                contentDescription = null,
            )
            Text(
                text = stringResource(R.string.orders),
                modifier = Modifier.padding(8.dp)
            )
            Spacer(Modifier.weight(1f))
            Icon(
                painter = painterResource(R.drawable.ic_arrow_forward),
                contentDescription = null,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        ElevatedButton(
            onClick = { showSignOutDialog = true },
            colors = ButtonDefaults
                .elevatedButtonColors(containerColor = MaterialTheme.colorScheme.error),
        ) {
            Row(
                modifier = Modifier
                    .width(120.dp)
                    .padding(8.dp)
                    .wrapContentWidth(align = Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_sign_out),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onError
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.sign_out),
                    color = MaterialTheme.colorScheme.onError
                )
            }

        }
        if (showSignOutDialog) {
            SignOutDialog(
                onDismiss = { showSignOutDialog = false },
                onSignOut = viewModel::signOut
            )
        }
        when (viewModel.message) {
            AccountMsg.NONE -> {}
            AccountMsg.LOADING -> {
                LoadingDialog()
            }

            AccountMsg.SUCCESS -> {
                EcomErrorDialog(
                    iconId = R.drawable.ic_success,
                    textId = R.string.success,
                    onDismissRequest = {},
                )
                LaunchedEffect(Unit) {
                    coroutineScope.launch {
                        delay(2_500)
                        showEditInfoCard = false
                        viewModel.resetMsg()
                        viewModel.cancelEditInfo()
                    }
                }
            }

            else -> {
                EcomErrorDialog(
                    iconId = R.drawable.ic_error,
                    textId = viewModel.message.msgId,
                    onDismissRequest = {
                        viewModel.resetMsg()
                    },
                )
            }
        }
    }
}
