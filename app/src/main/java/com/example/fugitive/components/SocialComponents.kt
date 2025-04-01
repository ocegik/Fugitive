package com.example.fugitive.components

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fugitive.R
import com.example.fugitive.ui.theme.FugitiveColors
import com.example.fugitive.utils.handleGoogleSignInResult
import com.example.fugitive.utils.startGoogleSignIn
import com.example.fugitive.viewmodels.AuthViewModel

@Composable
fun SocialLoginRow(authViewModel: AuthViewModel){
    val context = LocalContext.current
    val googleSignInLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            result.data?.let { handleGoogleSignInResult(it, authViewModel) }
        }
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedButton(
            onClick = {
                val activity = context as? ComponentActivity
                activity?.startGoogleSignIn(googleSignInLauncher, authViewModel)
            },
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = FugitiveColors.buttonText
            ),
            border = BorderStroke(2.dp, FugitiveColors.button),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google",
                    modifier = Modifier
                        .size(28.dp)
                        .padding(end = 8.dp),
                    tint = Color.Unspecified
                )
                Text(text = "Continue With Google",
                    color = FugitiveColors.buttonText)
            }
        }
    }
}