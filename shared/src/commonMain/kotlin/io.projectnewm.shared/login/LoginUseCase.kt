package io.projectnewm.shared.login

interface LoginUseCase

//TODO: To be implemented
internal class LoginUseCaseImpl(private val repository: LogInRepository) : LoginUseCase