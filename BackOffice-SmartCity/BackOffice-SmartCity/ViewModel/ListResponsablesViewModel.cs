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

namespace BackOffice_SmartCity.ViewModel
{
    public class ListResponsablesViewModel : ViewModelBase
    {
        private INavigationService navigationService;
        private ICommand navigateToChoix, navigateToNew, navigateToAcceuil, delete;
        private Responsable _selectedResponsable;

        public ListResponsablesViewModel(INavigationService navigationService)
        {
            this.navigationService = navigationService;
            InitializeAsync();
        }

        public ICommand NavigateToEdit                                              //Le getter de "l'action event"
        {
            get
            {
                if (this.navigateToChoix == null)
                    this.navigateToChoix = new RelayCommand(() => GoToEdit());       //On lui attribue la méthode EditStudent qui lui permet de naviguer la 2ème page !
                return this.navigateToChoix;
            }
        }

        private void GoToEdit()
        {
            if (CanExecute())
            {
                navigationService.NavigateTo("ModifierResponsable", SelectedResponsable);
            }            
        }

        public ICommand NavigateToNew                                              //Le getter de "l'action event"
        {
            get
            {
                if (this.navigateToNew == null)
                    this.navigateToNew = new RelayCommand(() => GoToNew());       //On lui attribue la méthode EditStudent qui lui permet de naviguer la 2ème page !
                return this.navigateToNew;
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
                if (this.navigateToAcceuil == null)
                    this.navigateToAcceuil = new RelayCommand(() => GoToAcceuil());
                return this.navigateToAcceuil;
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
                    return;                 //On ne le fait que si la valeur a change pour evite de passer au RaisePropertyChanged pour ne pas perturber les ecouteurs 
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

        public ICommand DeleteResponsableCommand                                            //Le getter de "l'action event"
        {
            get
            {
                if (this.delete == null)
                    this.delete = new RelayCommand(() => DeleteResponsable());       //On lui attribue la méthode qui lui permet de supprimer le responsable !
                return this.delete;
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
                    RaisePropertyChanged("SelectedResponsable");                            //Permet ici de détecter qu'un responsable a été coché dans la liste
                }
            }
        }

        public async Task DeleteResponsable()
        {
            if(CanExecute())
            {
                var service = new ResponsibleController();
                var delResp = service.DeleteRespon(SelectedResponsable.RegistrationNumber);
            }
        }

        private bool CanExecute()
        {
            return SelectedResponsable != null;
        }

    }
}
