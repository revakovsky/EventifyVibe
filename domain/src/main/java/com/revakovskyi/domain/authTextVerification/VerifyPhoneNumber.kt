package com.revakovskyi.domain.authTextVerification

import com.revakovskyi.domain.models.Status

internal class VerifyPhoneNumber {

    fun verify(phoneNumber: String): Status {
        return if (phoneNumber.isNotEmpty()) {
            if (isPhoneNumberValid(phoneNumber)) Status.Correct
            else if (isIncorrectPhoneNumberLength(phoneNumber)) Status.Incorrect
            else Status.Neutral
        } else Status.Neutral
    }

    private fun isPhoneNumberValid(login: String): Boolean {
        val phonePattern = Regex("""^\+380\d{9}$""")
        return phonePattern.matches(login)
    }

    private fun isIncorrectPhoneNumberLength(inputText: String) =
        inputText.startsWith("+380") && inputText.length > 13

}