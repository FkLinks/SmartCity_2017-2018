using BackOffice_SmartCity.Model;
using BackOffice_SmartCity.Service;
using GalaSoft.MvvmLight;
using GalaSoft.MvvmLight.Command;
using GalaSoft.MvvmLight.Views;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Threading.Tasks;
using System.Windows.Input;

namespace BackOffice_SmartCity.ViewModel
{
    public class ListJardinViewModel : ViewModelBase
    {
        private INavigationService navigationService;
        private ICommand delete;
        private Jardin _selectedJardin;

        public ListJardinViewModel(INavigationService navigationService)
        {
            this.navigationService = navigationService;
            InitializeAsync();
        }

        public ICommand NavigateToEdit                                              //Le getter de "l'action event"
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

        public ICommand NavigateToNew                                              //Le getter de "l'action event"
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
                    return;                 //On ne le fait que si la valeur a change pour evite de passer au RaisePropertyChanged pour ne pas perturber les ecouteurs 
                }
                _jardin = value;
                RaisePropertyChanged("Jardin");
            }
        }

        public async Task InitializeAsync()
        {
            var service = new GardenController();
            var listeGarden = await service.GetAllElements();
            Jardin = new ObservableCollection<Jardin>(listeGarden);
        }

        public ICommand DeleteGardenCommand                                            //Le getter de "l'action event"
        {
            get
            {
                if (this.delete == null)
                    this.delete = new RelayCommand(() => DeleteGarden());       //On lui attribue la méthode qui lui permet de supprimer le responsable !
                return this.delete;
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
                    RaisePropertyChanged("SelectedGarden");                            //Permet ici de détecter qu'un responsable a été coché dans la liste
                }
            }
        }

        public async Task DeleteGarden()
        {
            if (CanExecute())
            {
                var service = new GardenController();
                var delResp = service.DeleteGarden(SelectedGarden.NumGarden);
            }
        }

        private bool CanExecute()
        {
            return SelectedGarden != null;
        }

    }
}