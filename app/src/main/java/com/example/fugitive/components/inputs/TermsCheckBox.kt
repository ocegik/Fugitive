package com.example.fugitive.components.inputs

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.navigation.NavController
import com.example.fugitive.ui.theme.FugitiveColors
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import com.example.fugitive.navigation.Screen
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

@Composable
fun TermsCheckbox(isChecked: Boolean, onCheckedChange: (Boolean) -> Unit, navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )

        val annotatedString = buildAnnotatedString {
            withStyle(SpanStyle(color = FugitiveColors.subheading)) {
                append("Agree With ")
            }
            pushStringAnnotation(tag = "Terms & Conditions", annotation = "Terms & Conditions")
            withStyle(SpanStyle(color = FugitiveColors.button, textDecoration = TextDecoration.Underline)) {
                append("Terms & Conditions")
            }
            pop()
        }

        Text(
            text = annotatedString,
            modifier = Modifier
                .clickable { navController.navigate(Screen.Terms.route) }
                .padding(start = 8.dp)
        )
    }
}