package br.ufpa.genewhale.di

import br.ufpa.genewhale.docker.PanarooService
import br.ufpa.genewhale.ui.viewmodels.Global
import br.ufpa.genewhale.ui.viewmodels.HomeViewModel
import br.ufpa.genewhale.ui.viewmodels.ProjectViewModel
import br.ufpa.genewhale.ui.viewmodels.tools.PanarooViewModel
import br.ufpa.genewhale.web.WebService
import br.ufpa.genewhale.web.WebServiceJavaImpl
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

 val appModule = module {
    single { Global(get(), get()) }
    single { PanarooService() }
}

 val viewModelsModule = module {
    viewModelOf(::ProjectViewModel)
    viewModelOf(::PanarooViewModel)
    viewModelOf(::HomeViewModel)
}

 val webModules = module {
    single <WebService>{ WebServiceJavaImpl() }
}