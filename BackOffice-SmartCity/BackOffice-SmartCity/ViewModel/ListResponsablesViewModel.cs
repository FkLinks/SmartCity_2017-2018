using BackOffice_SmartCity.Model;
using BackOffice_SmartCity.Service;
using GalaSoft.MvvmLight;
using GalaSoft.MvvmLight.Command;
using GalaSoft.MvvmLight.Views;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Net.NetworkInformation;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;
using Windows.UI.Popups;

namespace BackOffice_SmartCity.ViewModel
{
    public class ListResponsablesViewModel : ViewModelBase
    {
        private INavigationService navigationService;
        private Responsable _selectedResponsable;
        private ObservableCollection<Responsable> _responsible = null;

        public ObservableCollection<Responsable> Responsable
        {
            get
            {
                return _responsible;
            }

            set
            {
                if (_responsible == value)
                {
                    return;
                }
                _responsible = value;
                RaisePropertyChanged("Responsable");
            }
        }

        public Responsable SelectedResponsable
        {
            get { return _selectedResponsable; }
            set
            {
                _selectedResponsable = value;
                if (_selectedResponsable != null)
                {
                    RaisePropertyChanged("SelectedResponsable");
                }
            }
        }

        public ListResponsablesViewModel(INavigationService navigationService)
        {
            this.navigationService = navigationService;
            InitializeAsync();
        }

        public ICommand NavigateToEdit
        {
            get
            {
                return new RelayCommand(() => GoToEdit());
            }
        }

        private void GoToEdit()
        {
            if (CanExecute())
            {
                navigationService.NavigateTo("ModifierResponsable", SelectedResponsable);
            }            
        }

        public ICommand NavigateToNew
        {
            get
            {
                return new RelayCommand(() => GoToNew());
            }
        }

        private void GoToNew()
        {
            navigationService.NavigateTo("CreationResponsable");
        }

        public ICommand NavigateToAcceuil
        {
            get
            {
                return new RelayCommand(() => GoToAcceuil());
            }
        }

        private void GoToAcceuil()
        {
            navigationService.GoBack();
        }

        public async Task InitializeAsync()
        {
            if (NetworkInterface.GetIsNetworkAvailable())
            {
                ResponsibleController service = new ResponsibleController();
                IEnumerable<Responsable> listeResponsable = await service.GetAllElements();
                Responsable = new ObservableCollection<Responsable>(listeResponsable);
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

        public ICommand DeleteResponsableCommand
        {
            get
            {
                return new RelayCommand(async () => await DeleteResponsable());
            }

        }

        public async Task DeleteResponsable()
        {
            if(CanExecute() && NetworkInterface.GetIsNetworkAvailable())
            {
                ResponsibleController service = new ResponsibleController();

                MessageDialog messageDialog = new MessageDialog(Constantes.MESSAGE_SUPPRESSION + SelectedResponsable.Login + " ?")
                {
                    Title = Constantes.TITRE_SUPPRESSION
                };
                messageDialog.Commands.Add(new UICommand { Label = "Oui", Id = 0 });
                messageDialog.Commands.Add(new UICommand { Label = "Non", Id = 1 });
                var res = await messageDialog.ShowAsync();

                if ((int)res.Id == 0)
                {
                    Task delResp = service.DeleteRespon(SelectedResponsable.RegistrationNumber);
                    await InitializeAsync();
                }
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

        private bool CanExecute()
        {
            return SelectedResponsable != null;
        }

        public ICommand Actualiser
        {
            get
            {
                return new RelayCommand(async () => await InitializeAsync());
            }
        }

    }
}
