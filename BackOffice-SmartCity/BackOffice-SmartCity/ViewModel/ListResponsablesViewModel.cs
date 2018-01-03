using BackOffice_SmartCity.Model;
using BackOffice_SmartCity.Service;
using GalaSoft.MvvmLight;
using GalaSoft.MvvmLight.Command;
using GalaSoft.MvvmLight.Views;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
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

        public async Task InitializeAsync()
        {
            var service = new ResponsibleController();
            var listeResponsable = await service.GetAllElements();
            Responsable = new ObservableCollection<Responsable>(listeResponsable);
        }

        public ICommand DeleteResponsableCommand
        {
            get
            {
                return new RelayCommand(async () => await DeleteResponsable());
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

        public async Task DeleteResponsable()
        {
            if(CanExecute())
            {
                var service = new ResponsibleController();

                var messageDialog = new MessageDialog(Constantes.MESSAGE_SUPPRESSION + SelectedResponsable.Login + " ?")
                {
                    Title = Constantes.TITRE_SUPPRESSION
                };
                messageDialog.Commands.Add(new UICommand { Label = "Oui", Id = 0 });
                messageDialog.Commands.Add(new UICommand { Label = "Non", Id = 1 });
                var res = await messageDialog.ShowAsync();

                if ((int)res.Id == 0)
                {
                    var delResp = service.DeleteRespon(SelectedResponsable.RegistrationNumber);
                    await InitializeAsync();
                }
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
