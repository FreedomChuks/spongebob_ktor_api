package com.freedom.plugins

import com.freedom.data.dao.CharacterDaoInterface
import com.freedom.data.dao.CharacterDaoImpl
import com.freedom.domain.usecase.AddCharacters
import com.freedom.domain.usecase.GetAllCharacter
import com.freedom.domain.usecase.GetCharacter
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(characterModule)
    }
}
val characterModule = module {
    single<CharacterDaoInterface> { CharacterDaoImpl() }
    single { AddCharacters(get()) }
    single { GetAllCharacter(get()) }
    single { (GetCharacter(get())) }
}