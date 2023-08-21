package com.revakovskyi.featureauth.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.revakovskyi.core.presentation.ui.theme.dimens
import com.revakovskyi.core.presentation.widgets.BackButton
import com.revakovskyi.core.presentation.widgets.ButtonRegular
import com.revakovskyi.core.presentation.widgets.TextRegular
import com.revakovskyi.core.presentation.widgets.TextTitle
import com.revakovskyi.featureauth.R
import com.revakovskyi.featureauth.presentation.models.AuthInputTextType
import com.revakovskyi.featureauth.presentation.models.ValidationStatus
import com.revakovskyi.featureauth.presentation.widgets.LoginInputField
import com.revakovskyi.featureauth.presentation.widgets.SendInstructionsDialog
import com.revakovskyi.featureauth.viewModel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
internal fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AuthViewModel,
) {
    var email by remember { mutableStateOf("") }
    var isDialogShown by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarMessage = stringResource(id = R.string.we_have_resend_instructions)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { contentPadding ->

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {

            BackButton(
                onClick = { navController.popBackStack() },
                icon = Icons.Default.Close
            )

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.dimens.medium)
            ) {

                TextTitle(
                    modifier = Modifier.padding(top = MaterialTheme.dimens.large),
                    text = stringResource(R.string.forgot_password)
                )

                TextRegular(
                    modifier = Modifier.padding(
                        horizontal = MaterialTheme.dimens.medium,
                        vertical = MaterialTheme.dimens.large
                    ),
                    text = stringResource(R.string.enter_your_email),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                LoginInputField(
                    status = viewModel.emailValidationStatus,
                    inputLogin = { inputLogin ->
                        viewModel.apply {
                            verifyInputText(inputLogin, AuthInputTextType.Email)
                            email =
                                if (emailValidationStatus == ValidationStatus.Correct) inputLogin
                                else ""
                        }
                    },
                    icon = R.drawable.email,
                    label = stringResource(id = R.string.email),
                    placeholder = stringResource(id = R.string.email_example),
                    supportingText = stringResource(id = R.string.email_is_incorrect),
                    imeAction = ImeAction.Done,
                )

                ButtonRegular(
                    buttonText = stringResource(R.string.send_message),
                    enabled = email.isNotEmpty(),
                    onClick = { isDialogShown = true }
                )

            }

        }

    }

    if (isDialogShown) {
        SendInstructionsDialog(
            email = email,
            onConfirm = { navController.popBackStack() },
            onDismiss = { isDialogShown = false },
            onResendMessageClicked = {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(snackbarMessage)
                }
            }
        )
    }

}
