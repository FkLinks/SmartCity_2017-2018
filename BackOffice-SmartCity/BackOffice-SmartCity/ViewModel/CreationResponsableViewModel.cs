using GalaSoft.MvvmLight;
using GalaSoft.MvvmLight.Command;
using GalaSoft.MvvmLight.Views;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace BackOffice_SmartCity.ViewModel
{
    public class CreationResponsableViewModel : ViewModelBase
    {
        private INavigationService navigationService;
        private ICommand navigateToChoix, navigateToBefore;

        public CreationResponsableViewModel(INavigationService navigationService)
        {
            this.navigationService = navigationService;
        }

        public ICommand NavigateToNextPage                                              //Le getter de "l'action event"
        {
            get
            {
                if (this.navigateToChoix == null)
                    this.navigateToChoix = new RelayCommand(() => GoToAcceuil());       //On lui attribue la méthode EditStudent qui lui permet de naviguer la 2ème page !
                return this.navigateToChoix;
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
                if (this.navigateToBefore == null)
                    this.navigateToBefore = new RelayCommand(() => GoTBefore());
                return this.navigateToBefore;
            }
        }

        private void GoTBefore()
        {
            navigationService.GoBack();
        }
    }
}
