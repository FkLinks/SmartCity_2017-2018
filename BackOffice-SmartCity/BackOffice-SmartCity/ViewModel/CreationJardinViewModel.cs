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

namespace BackOffice_SmartCity.ViewModel
{
    public class CreationJardinViewModel
    {
        private INavigationService navigationService;

        public CreationJardinViewModel(INavigationService navigationService)
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

        /*public ICommand CreationJardin
        {
            
        }*/

        public void PostJardin(Jardin jardin)
        {
            var service = new GardenController();
            var listeGarden =  service.PostGarden(jardin);
        }

        private void GoTBefore()
        {
            navigationService.GoBack();
        }
    }
}
