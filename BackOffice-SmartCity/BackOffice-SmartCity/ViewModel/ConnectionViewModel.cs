using BackOffice_SmartCity.Model;
using BackOffice_SmartCity.Service;
using GalaSoft.MvvmLight;
using GalaSoft.MvvmLight.Command;
using GalaSoft.MvvmLight.Views;
using System;
using System.Threading.Tasks;
using System.Windows.Input;
using Windows.UI.Popups;

namespace BackOffice_SmartCity.ViewModel
{
    public class ConnectionViewModel : ViewModelBase
    {
        private INavigationService navigationService;
        private Login _admin;

        public Login AdminUWP
        {
            get
            {
                return _admin;
            }
            set
            {
                if (value == _admin)
                {
                    return;
                }
                _admin = value;
                RaisePropertyChanged("AdminUWP");
            }
        }

        public ConnectionViewModel(INavigationService navigationService)
        {
            this.navigationService = navigationService;
            AdminUWP = new Login();
        }

        public ICommand NavigateToAcceuil                                       
        {
            get
            {
                return new RelayCommand(async () => await GetTokens());
            }
        }    

        private void GoToAcceuil()
        {
            navigationService.NavigateTo("Acceuil");
        }
        
        public async Task GetTokens()
        {
            AccountController service = new AccountController();
            Constantes.TOKEN_ADMIN_PROP = await service.GetTokenAdmin(AdminUWP);

            if(Constantes.TOKEN_ADMIN_PROP != null)
            {
                navigationService.NavigateTo("Acceuil");
            }
            else
            {
                MessageDialog messageDialog = new MessageDialog(Constantes.BAD_CONNECTION)
                {
                    Title = Constantes.TITRE_ERREUR_CONNECTION
                };
                var res = messageDialog.ShowAsync();
            }
        }
    }
}
