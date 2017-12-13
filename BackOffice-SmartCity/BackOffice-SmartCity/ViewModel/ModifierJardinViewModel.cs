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
    public class ModifierJardinViewModel
    {
        private INavigationService navigationService;
        public Jardin SelectedJardin { get; set; }

        public ModifierJardinViewModel(INavigationService navigationService)
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
                return new RelayCommand(() => PutGarden());
            }
        }

        public async Task PutGarden()
        {
            if (CanExecute())
            {
                var service = new GardenController();
                var delResp = service.PutGarden(SelectedJardin);
            }
        }

        private bool CanExecute()
        {
            //TODO : FAIRE LES TESTS DE VALIDATION DES CHAMPS
            return true;
        }

        public void OnNavigatedTo(NavigationEventArgs e)
        {
            SelectedJardin = (Jardin)e.Parameter;
        }
    }
}
