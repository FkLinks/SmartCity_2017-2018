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
using Windows.UI.Xaml.Navigation;

namespace BackOffice_SmartCity.ViewModel
{
    public class ModifierResponsableViewModel : ViewModelBase
    {
        private INavigationService navigationService;
        public Responsable SelectedRespon { get; set; }

        public ModifierResponsableViewModel(INavigationService navigationService)
        {
            this.navigationService = navigationService;
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

        private void GoTBefore()
        {
            navigationService.GoBack();
        }

        public ICommand ConfirmChange
        {
            get
            {
                return new RelayCommand(async () => await PutRespon());
            }
        }

        public async Task PutRespon()
        {
            ResponsibleController service = new ResponsibleController();
            var delResp = await service.PutResponsable(SelectedRespon);
            if (delResp.Equals("No Content"))
            {
                MessageDialog messageDialog = new MessageDialog(Constantes.MESSAGE_OK_MODIF)
                {
                    Title = Constantes.TITRE_MODIF_OK
                };
                var res = await messageDialog.ShowAsync();
                navigationService.NavigateTo("Acceuil");
            }
            else
            {
                MessageDialog messageDialog = new MessageDialog(Constantes.MESSAGE_ERREUR_MODIF)
                {
                    Title = Constantes.TITRE_ERREUR_MODIF
                };
                var res = await messageDialog.ShowAsync();
            }
        }

        public void OnNavigatedTo(NavigationEventArgs e)
        {
            SelectedRespon = (Responsable)e.Parameter;
        }
    }
}
