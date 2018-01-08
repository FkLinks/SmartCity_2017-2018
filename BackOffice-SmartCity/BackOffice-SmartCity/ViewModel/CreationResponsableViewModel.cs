using BackOffice_SmartCity.Model;
using BackOffice_SmartCity.Service;
using GalaSoft.MvvmLight;
using GalaSoft.MvvmLight.Command;
using GalaSoft.MvvmLight.Views;
using System;
using System.Collections.ObjectModel;
using System.Net.NetworkInformation;
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
                RaisePropertyChanged("NewResponsable");
            }
        }

        #region ComboBox_Sex

        private ObservableCollection<String> _sex;
        public ObservableCollection<String> Sex
        {
            get
            {
                return _sex;
            }

            set
            {
                if (_sex == value)
                {
                    return;
                }
                _sex = value;
                RaisePropertyChanged("Sex");
            }
        }

        private String _selectedSex;
        public String SelectedSex
        {
            get { return _selectedSex; }
            set
            {
                _selectedSex = value;
                if (_selectedSex != null)
                {
                    RaisePropertyChanged("SelectedSex");
                }
            }
        }

        #endregion

        public CreationResponsableViewModel(INavigationService navigationService)
        {
            this.navigationService = navigationService;
            NewResponsable = new Responsable();
            Sex = new ObservableCollection<String>(Constantes.Sex);
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
            if (NetworkInterface.GetIsNetworkAvailable())
            {
                ResponsibleController service = new ResponsibleController();
                responsable.Sex = SelectedSex;
                var respon = await service.PostResponsable(responsable);

                if (respon.Equals("Created"))
                {
                    navigationService.NavigateTo("Acceuil");
                    NewResponsable = new Responsable();
                }
                else
                {
                    MessageDialog messageDialog = new MessageDialog(Constantes.MESSAGE_ERREUR)
                    {
                        Title = Constantes.TITRE_ERREUR
                    };
                    var res = await messageDialog.ShowAsync();
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
    }
}
