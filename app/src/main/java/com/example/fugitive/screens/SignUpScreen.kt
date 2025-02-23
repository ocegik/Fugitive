package com.example.fugitive.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fugitive.R
import com.example.fugitive.Screen
import com.example.fugitive.ui.theme.FugitiveColors
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

@Composable
fun SignUpScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var termsCond by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Create Account",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = FugitiveColors.heading
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Enter your details below or continue with your social account",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = FugitiveColors.subheading
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            placeholder = { Text("Enter your name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            placeholder = { Text("Enter your email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()

        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            placeholder = { Text("Enter your password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Checkbox(
                checked = termsCond,
                onCheckedChange = { termsCond = it }
            )


            val annotatedString = buildAnnotatedString {
                append("Agree With ")
                pushStringAnnotation(tag = "Terms & Conditions", annotation = "Terms & Conditions")
                withStyle(
                    style = androidx.compose.ui.text.SpanStyle(
                        color = FugitiveColors.button,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Terms & Conditions")
                }
                pop()
            }

            Text(
                text = annotatedString,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.Terms.route)
                }
                    .padding(start = 8.dp)
            )
        }



        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { navController.navigate(Screen.Home.route) },
            colors = ButtonDefaults.buttonColors(containerColor = FugitiveColors.button),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )
        {
            Text("Sign Up", color = FugitiveColors.buttonText)
        }
        Spacer(modifier = Modifier.height(20.dp))

        Text("Or Sign Up With", color = FugitiveColors.subheading)

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        )
        {

            Icon(
                painterResource(id = R.drawable.ic_google),
                contentDescription = "Google",
                modifier = Modifier.size(40.dp),
                tint = Color.Unspecified
            )

            Icon(
                painterResource(
                    id = R.drawable.icons_facebook
                ),
                contentDescription = "Facebook",
                modifier = Modifier.size(40.dp),
                tint = Color.Unspecified
            )

            Icon(
                painterResource(
                    id = R.drawable.icons_x
                ),
                contentDescription = "X",
                modifier = Modifier.size(40.dp),
                tint = Color.Unspecified
            )
        }
    }
}




