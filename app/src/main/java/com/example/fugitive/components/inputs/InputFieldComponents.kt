package com.example.fugitive.components.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation


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
fun PassInputField(password: String,
                   onPasswordChange: (String) -> Unit,
                   label: String = "Password", // Customizable Label
                   placeholder: String = "Enter your password", // Customizable Placeholder
                   modifier: Modifier = Modifier.fillMaxWidth(), // Allowing Modifier Customization
                   isError: Boolean = false // Optional Error State
) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
        modifier = modifier,
        isError = isError
    )
}

@Composable
fun NameInputField(
    name: String,
    onValueChange: (String) -> Unit,
    label: String = "Name",
    placeholder: String = "Enter your name",
    modifier: Modifier = Modifier.fillMaxWidth(), // Allowing Modifier Customization
    isError: Boolean = false //

){
    OutlinedTextField(
        value = name,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
        modifier = modifier,
        isError = isError
    )
}