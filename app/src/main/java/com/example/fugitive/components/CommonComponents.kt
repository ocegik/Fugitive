package com.example.fugitive.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fugitive.R
import com.example.fugitive.ui.theme.FugitiveColors


@Composable
fun EmailInputField(email: String, onEmailChange: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email") },
        placeholder = { Text("Enter your email") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier.fillMaxWidth()
    )
}


@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = FugitiveColors.button),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text, color = FugitiveColors.buttonText)
    }
}

@Composable
fun SocialLoginRow() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painterResource(id = R.drawable.ic_google),
            contentDescription = "Google",
            modifier = Modifier.size(40.dp),
            tint = Color.Unspecified
        )
        Icon(
            painterResource(id = R.drawable.icons_facebook),
            contentDescription = "Facebook",
            modifier = Modifier.size(40.dp),
            tint = Color.Unspecified
        )
        Icon(
            painterResource(id = R.drawable.icons_x),
            contentDescription = "X",
            modifier = Modifier.size(40.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun OtpTextField(
    otpLength: Int = 4,
    onOtpEntered: (String) -> Unit
) {
    val otpValues = remember { Array(otpLength) { mutableStateOf("") } }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        otpValues.forEachIndexed { index, state ->
            OutlinedTextField(
                value = state.value,
                onValueChange = { newValue ->
                    if (newValue.length <= 1) {
                        state.value = newValue
                        if (newValue.isNotEmpty() && index < otpLength - 1) {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    }
                    val otp = otpValues.joinToString("") { it.value }
                    if (otp.length == otpLength) {
                        onOtpEntered(otp)
                        keyboardController?.hide()
                    }
                },
                modifier = Modifier.size(50.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true
            )
        }
    }
}
