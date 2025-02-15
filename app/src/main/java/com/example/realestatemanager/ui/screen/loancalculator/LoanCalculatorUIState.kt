package com.example.realestatemanager.ui.screen.loancalculator

import com.example.realestatemanager.ui.form.state.FieldState


sealed class LoanCalculatorUIState{
    data class IsLoading(
        val formState: FormState
    ):LoanCalculatorUIState()

    data class Success(
        val monthlyPayment: Double,
        val formState: FormState
    ):LoanCalculatorUIState()

    data class Error(
        val sError : String?,
        val formState: FormState
    ):LoanCalculatorUIState()

    data class FormState(
        val amount: FieldState,
        val interestRate: FieldState,
        val duration: FieldState,
        val downPayment: FieldState,
        val isFormValid : Boolean
    ):LoanCalculatorUIState()
}
