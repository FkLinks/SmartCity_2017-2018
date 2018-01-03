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
    public class CreationResponsableViewModel : ViewModelBase
    {
        private INavigationService navigationService;
        public Responsable _newResponsable;
        public Responsable NewResponsable
        {
            get
            {
                return _newResponsable;
            }
            set
            {
                if (value == _newResponsable)
                {
                    return;
                }
                _newResponsable = value;
                RaisePropertyChanged("NewJardin");
            }
        }

        public CreationResponsableViewModel(INavigationService navigationService)
        {
            this.navigationService = navigationService;
            NewResponsable = new Responsable();
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

        public ICommand CreationResponsable
        {
            get
            {
                return new RelayCommand(async () => await PostResponsableAsync(NewResponsable));
            }
        }

        public async Task PostResponsableAsync(Responsable responsable)
        {
            var service = new ResponsibleController();
            var respon = await service.PostResponsable(responsable);

            if (respon.Equals("Created"))
            {                
                navigationService.NavigateTo("Acceuil");
                NewResponsable = null;
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
    }
}
