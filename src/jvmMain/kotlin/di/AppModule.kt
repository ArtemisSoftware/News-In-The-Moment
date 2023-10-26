package di

import data.repository.NewsRepositoryImpl
import domain.repository.NewsRepository
import org.koin.dsl.module

val appModule = module {
    single<NewsRepository> { NewsRepositoryImpl() }
}