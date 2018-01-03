using BackOffice_SmartCity.Model;
using BackOffice_SmartCity.Service;
using GalaSoft.MvvmLight;
using GalaSoft.MvvmLight.Command;
using GalaSoft.MvvmLight.Views;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;
using Windows.UI.Popups;

namespace BackOffice_SmartCity.ViewModel
{
    public class CreationJardinViewModel : ViewModelBase
    {
        private INavigationService navigationService;
        private Jardin _newJardin;
        public Jardin NewJardin
        {
            get
            {
                return _newJardin;
            }
            set
            {
                if(value == _newJardin)
                {
                    return;
                }
                _newJardin = value;
                RaisePropertyChanged("NewJardin");
            }
        }

        public CreationJardinViewModel(INavigationService navigationService)
        {
            this.navigationService = navigationService;
            NewJardin = new Jardin();
        }

        public ICommand NavigateToNextPage
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

       public ICommand CreationJardin
        {
            get
            {
                return new RelayCommand(async () => await PostJardinAsync(NewJardin));
            }      
        }

        public async Task PostJardinAsync(Jardin jardin)
        {
            var service = new GardenController();
            var createGarden = await service.PostGarden(jardin);

            if (createGarden.Equals("Created"))
            {
                navigationService.NavigateTo("Acceuil");
                NewJardin = null;
            }
            else
            {
                var messageDialog = new MessageDialog(Constantes.MESSAGE_ERREUR)
                {
                    Title = Constantes.TITRE_ERREUR
                };
                var res = await messageDialog.ShowAsync();
            }
        }

        private void GoTBefore()
        {
            navigationService.GoBack();
        }
    }
}