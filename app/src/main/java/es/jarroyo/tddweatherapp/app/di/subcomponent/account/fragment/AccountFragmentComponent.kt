package es.jarroyo.tddweatherapp.app.di.subcomponent.account.fragment

import dagger.Subcomponent
import es.jarroyo.tddweatherapp.ui.account.fragment.AccountFragment

@Subcomponent(modules = arrayOf(AccountFragmentModule::class))
interface AccountFragmentComponent {
    fun injectTo(fragment: AccountFragment)
}