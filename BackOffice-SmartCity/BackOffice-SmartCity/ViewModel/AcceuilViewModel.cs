using BackOffice_SmartCity.Model;
using BackOffice_SmartCity.Service;
using GalaSoft.MvvmLight;
using GalaSoft.MvvmLight.Command;
using GalaSoft.MvvmLight.Views;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;
using Windows.UI.Xaml.Navigation;

namespace BackOffice_SmartCity.ViewModel
{
    public class AcceuilViewModel : ViewModelBase
    {
        private INavigationService navigationService;

        public AcceuilViewModel(INavigationService navigationService)
        {
            this.navigationService = navigationService;
        }

        public ICommand NavigateToResponsable                                             
        {
            get
            {
                return new RelayCommand(() => GoToResponsable());
            }
        }

        private void GoToResponsable()
        {
            navigationService.NavigateTo("ListResponsables");
        }

        public ICommand NavigateToJardin
        {
            get
            {
                return new RelayCommand(() => GoToJardin()); 
            }
        }

        private void GoToJardin()
        {
            navigationService.NavigateTo("ListJardin");
        }

        public ICommand NavigateToUtilisateur
        {
            get
            {
                return new RelayCommand(() => GoToUtilisateur());
            }
        }

        private void GoToUtilisateur()
        {
            navigationService.NavigateTo("Utilisateur");
        }

        public ICommand NavigateToInfo
        {
            get
            {
                return new RelayCommand(() => GoToInfo());
            }
        }

        private void GoToInfo()
        {
            navigationService.NavigateTo("InfosAdmin");
        }
    }
}
