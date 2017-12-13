using GalaSoft.MvvmLight.Command;
using GalaSoft.MvvmLight.Views;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace BackOffice_SmartCity.ViewModel
{
    public class ConnectionViewModel
    {
        private INavigationService navigationService;
        private ICommand navigateToResponsable;

        public ConnectionViewModel(INavigationService navigationService)
        {
            this.navigationService = navigationService;
        }

        public ICommand NavigateToAcceuil                                              //Le getter de "l'action event"
        {
            get
            {
                if (this.navigateToResponsable == null)
                    this.navigateToResponsable = new RelayCommand(() => GoToChoix());       //On lui attribue la méthode EditStudent qui lui permet de naviguer la 2ème page !
                return this.navigateToResponsable;
            }
        }

        private void GoToChoix()
        {
            navigationService.NavigateTo("Acceuil");
        }      
        
        /*public async Task<String> getTokens()                               //POUR LA CONNECTION
        {
            var webClient = new HttpClient();
            var admin = await webClient.GetStringAsync(new Uri("http://smartcity-jardin-20172018.azurewebsites.net/api/Account/AdminUWP"));
            //webClient.DefaultRequestHeaders.Accept.Clear();
            //webClient.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("/application/json"));
            
            
        }*/
    }
}
