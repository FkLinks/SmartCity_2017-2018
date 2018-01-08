using BackOffice_SmartCity.Model;
using BackOffice_SmartCity.Service;
using GalaSoft.MvvmLight;
using GalaSoft.MvvmLight.Command;
using GalaSoft.MvvmLight.Views;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Net.NetworkInformation;
using System.Threading.Tasks;
using System.Windows.Input;
using Windows.UI.Popups;

namespace BackOffice_SmartCity.ViewModel
{
    public class ListJardinViewModel : ViewModelBase
    {
        private INavigationService navigationService;
        private Jardin _selectedJardin;
        private ObservableCollection<Jardin> _jardin = null;

        public ObservableCollection<Jardin> Jardin
        {
            get
            {
                return _jardin;
            }

            set
            {
                if (_jardin == value)
                {
                    return;
                }
                _jardin = value;
                RaisePropertyChanged("Jardin");
            }
        }

        public Jardin SelectedGarden
        {
            get { return _selectedJardin; }
            set
            {
                _selectedJardin = value;
                if (_selectedJardin != null)
                {
                    RaisePropertyChanged("SelectedGarden");
                }
            }
        }

        public ListJardinViewModel(INavigationService navigationService)
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
            if(CanExecute())
            {
                navigationService.NavigateTo("ModifierJardin", SelectedGarden);
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
            navigationService.NavigateTo("CreationJardin");
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
                GardenController service = new GardenController();
                IEnumerable<Jardin> listeGarden = await service.GetAllElements();
                Jardin = new ObservableCollection<Jardin>(listeGarden);
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

        public ICommand DeleteGardenCommand
        {
            get
            {
                return new RelayCommand(async () => await DeleteGarden());
            }

        }

        public async Task DeleteGarden()
        {
            if (CanExecute() && NetworkInterface.GetIsNetworkAvailable())
            {
                GardenController service = new GardenController();

                MessageDialog messageDialog = new MessageDialog(Constantes.MESSAGE_SUPPRESSION + SelectedGarden.Name + " ?")
                {
                    Title = Constantes.TITRE_SUPPRESSION
                };
                messageDialog.Commands.Add(new UICommand { Label = "Oui", Id = 0 });
                messageDialog.Commands.Add(new UICommand { Label = "Non", Id = 1 });
                var res = await messageDialog.ShowAsync();

                if ((int)res.Id == 0)
                {
                    Task delResp = service.DeleteGarden(SelectedGarden.NumGarden);
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
            return SelectedGarden != null;
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