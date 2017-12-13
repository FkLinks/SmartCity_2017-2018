using BackOffice_SmartCity.Model;
using BackOffice_SmartCity.Service;
using GalaSoft.MvvmLight.Command;
using GalaSoft.MvvmLight.Views;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;
using Windows.UI.Xaml.Navigation;

namespace BackOffice_SmartCity.ViewModel
{
    public class ModifierResponsableViewModel
    {
        private INavigationService navigationService;
        public Responsable SelectedRespon { get; set; }

        public ModifierResponsableViewModel(INavigationService navigationService)
        {
            this.navigationService = navigationService;
        }

        public ICommand NavigateToNextPage                                              //Le getter de "l'action event"
        {
            get
            {
                return new RelayCommand(() => GoToAcceuil());
            }
        }

        private void GoToAcceuil()
        {
            navigationService.NavigateTo("Acceuil");
        }

        public ICommand NavigateToBefore
        {
            get
            {
                return new RelayCommand(() => GoTBefore());
            }
        }

        private void GoTBefore()
        {
            navigationService.GoBack();
        }

        public ICommand ConfirmChange
        {
            get
            {
                return new RelayCommand(() => PutRespon());
            }
        }

        public async Task PutRespon()
        {
            if (CanExecute())
            {
                var service = new ResponsibleController();
                var delResp = service.PutResponsable(SelectedRespon);
            }
        }

        private bool CanExecute()
        {
            //TODO : FAIRE LES TESTS DE VALIDATION DES CHAMPS
            return true;
        }

        public void OnNavigatedTo(NavigationEventArgs e)
        {
            SelectedRespon = (Responsable)e.Parameter;
        }
    }
}
