package com.route.ecommerce.ui.screens.account

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.route.ecommerce.R
import com.route.model.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@Composable
fun SignedInScreen(
    coroutineScope: CoroutineScope,
    viewModel: AccountViewModel,
    userData: UserData,
    modifier: Modifier = Modifier,
) {
    var showSignOutDialog by remember { mutableStateOf(false) }
    var showEditProfileDialog by remember { mutableStateOf(false) }
    var showOrdersDialog by remember { mutableStateOf(false) }
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
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = userData.userImgPath.ifEmpty {
                R.drawable.ic_person
            },
            contentDescription = stringResource(R.string.user_image),
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onSurfaceVariant, CircleShape)
                .clickable {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop
        )

        Text(
            text = userData.userName,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = userData.userEmail,
            style = MaterialTheme.typography.titleSmall
        )

        Card(
            onClick = { showEditProfileDialog = true },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = null,
                )
                Text(
                    text = stringResource(R.string.edit_profile),
                )
            }

        }

        Card(
            onClick = { showOrdersDialog = true },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_orders),
                    contentDescription = null,
                )
                Text(
                    text = stringResource(R.string.orders),
                )
            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        ElevatedCard(
            onClick = { showSignOutDialog = true },

            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_sign_out),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
                Text(
                    text = stringResource(R.string.sign_out),
                    color = MaterialTheme.colorScheme.error
                )
            }

        }
        if (showSignOutDialog) {
            SignOutDialog(
                onDismiss = { showSignOutDialog = false },
                onSignOut = viewModel::signOut
            )
        }
    }
}

@Composable
fun SignOutDialog(
    onDismiss: () -> Unit,
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onSignOut
            ) {

                Text(
                    text = stringResource(R.string.sign_out),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        icon = {
            Icon(
                painter = painterResource(R.drawable.ic_sign_out),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
        },
        text = {
            Text(stringResource(R.string.are_you_sure_you_want_to_sign_out))
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    )
}
