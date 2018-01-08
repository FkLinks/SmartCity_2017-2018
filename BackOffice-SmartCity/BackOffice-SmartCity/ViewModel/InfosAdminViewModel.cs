using BackOffice_SmartCity.Model;
using BackOffice_SmartCity.Service;
using GalaSoft.MvvmLight;
using GalaSoft.MvvmLight.Command;
using GalaSoft.MvvmLight.Views;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.NetworkInformation;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;
using Windows.UI.Popups;
using Windows.UI.Xaml.Navigation;

namespace BackOffice_SmartCity.ViewModel
{
    public class InfosAdminViewModel : ViewModelBase
    {
        private INavigationService navigationService;
        private int _nbUsers;
        private int _nbGardens;
        private int _nbRespon;

        public int NbUsers
        {
            get
            {
                return _nbUsers;
            }
            set
            {

                if (_nbUsers == value)
                {
                    return;
                }
                _nbUsers = value;
                RaisePropertyChanged("NbUsers");
            }
        }

        public int NbGardens
        {
            get
            {
                return _nbGardens;
            }
            set
            {

                if (_nbGardens == value)
                {
                    return;
                }
                _nbGardens = value;
                RaisePropertyChanged("NbGardens");
            }
        }

        public int NbRespon
        {
            get
            {
                return _nbRespon;
            }
            set
            {

                if (_nbRespon == value)
                {
                    return;
                }
                _nbRespon = value;
                RaisePropertyChanged("NbRespon");
            }
        }

        public InfosAdminViewModel(INavigationService navigationService)
        {
            this.navigationService = navigationService;
            InitializeAsync();
        }

        public ICommand NavigateToAccueil
        {
            get
            {
                return new RelayCommand(() => GoToAccueil());
            }
        }

        public ICommand Actualiser
        {
            get
            {
                return new RelayCommand(async () => await InitializeAsync());
            }
        }

        private void GoToAccueil()
        {            
            navigationService.NavigateTo("Acceuil");
        }

        public async Task InitializeAsync()
        {
            if (NetworkInterface.GetIsNetworkAvailable())
            {
                AccountController serviceAccount = new AccountController();
                NbUsers = await serviceAccount.CountUsers();

                GardenController serviceGarden = new GardenController();
                NbGardens = await serviceGarden.CountGardens();

                ResponsibleController serviceRespon = new ResponsibleController();
                NbRespon = await serviceRespon.CountRespon();
            }
            else
            {
                MessageDialog messageDialog = new MessageDialog(Constantes.MESSAGE_ERREUR_CONNEXION)
                {
                    Title = Constantes.TITRE_ERREUR_CONNECTION
                };
                var res = messageDialog.ShowAsync();
            }
        }

    }
}
