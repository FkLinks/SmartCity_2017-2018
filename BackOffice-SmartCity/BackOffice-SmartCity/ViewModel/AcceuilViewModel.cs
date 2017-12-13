using GalaSoft.MvvmLight.Command;
using GalaSoft.MvvmLight.Views;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace BackOffice_SmartCity.ViewModel
{
    public class AcceuilViewModel
    {
        private INavigationService navigationService;
        private ICommand navigateToResponsable, navigateToJardin, navigateToUti;

        public AcceuilViewModel(INavigationService navigationService)
        {
            this.navigationService = navigationService;
        }

        public ICommand NavigateToResponsable                                              //Le getter de "l'action event"
        {
            get
            {
                if (this.navigateToResponsable == null)
                    this.navigateToResponsable = new RelayCommand(() => GoToChoix());       //On lui attribue la méthode EditStudent qui lui permet de naviguer la 2ème page !
                return this.navigateToResponsable;
            }
        }

        private void GoToChoix()
        {
            navigationService.NavigateTo("ListResponsables");
        }

        public ICommand NavigateToJardin                                              //Le getter de "l'action event"
        {
            get
            {
                if (this.navigateToJardin == null)
                    this.navigateToJardin = new RelayCommand(() => GoToEdit());       //On lui attribue la méthode EditStudent qui lui permet de naviguer la 2ème page !
                return this.navigateToJardin;
            }
        }

        private void GoToEdit()
        {
            navigationService.NavigateTo("ListJardin");
        }

        public ICommand NavigateToUtilisateur                                             //Le getter de "l'action event"
        {
            get
            {
                if (this.navigateToUti == null)
                    this.navigateToUti = new RelayCommand(() => GoToDelete());       //On lui attribue la méthode EditStudent qui lui permet de naviguer la 2ème page !
                return this.navigateToUti;
            }
        }

        private void GoToDelete()
        {
            navigationService.NavigateTo("Utilisateur");
        }
    }
}
