package com.route.ecommerce.ui.screens.account

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.route.datastore.UserData
import com.route.ecommerce.R


@Composable
fun InfoCard(
    userData: UserData,
    onEditInfoClick: () -> Unit,
    onEditPasswordClick: () -> Unit,
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_account),
                contentDescription = null
            )
            Text(
                text = userData.userName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth()
            )
        }
        HorizontalDivider()
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_email),
                contentDescription = null
            )
            Text(
                text = userData.userEmail,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth()
            )
        }
        HorizontalDivider()
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_phone),
                contentDescription = null
            )
            Text(
                text = userData.userPhone.ifEmpty { stringResource(R.string.not_set) },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth()
            )
        }
        HorizontalDivider()
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_password),
                contentDescription = null
            )
            Text(
                text = "*".repeat(userData.userPassword.length),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth()
            )
        }
        Row(
            modifier = Modifier.padding(8.dp),
        ) {
            ElevatedButton(
                onClick = onEditInfoClick,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(horizontal = 4.dp),
                shape = MaterialTheme.shapes.extraSmall
            ) {
                Text(
                    text = stringResource(R.string.edit_info),
                    style = MaterialTheme.typography.labelLarge,
                )
            }

            ElevatedButton(
                onClick = onEditPasswordClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                shape = MaterialTheme.shapes.extraSmall
            ) {
                Text(
                    text = stringResource(R.string.edit_password),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}