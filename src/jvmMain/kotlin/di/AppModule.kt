package di

import data.repository.NewsRepositoryImpl
import domain.repository.NewsRepository
import domain.usecases.GetArticlesUseCase
import org.koin.dsl.module

val appModule = module {
    single<NewsRepository> { NewsRepositoryImpl() }
    single<GetArticlesUseCase> { GetArticlesUseCase(get()) }
}