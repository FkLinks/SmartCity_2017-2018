using BackOffice_SmartCity.Model;
using BackOffice_SmartCity.Service;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Popups;

namespace BackOffice_SmartCity.Service
{
    public class AccountController
    {
        private HttpClient http = Constantes.GetHttp();

        public async Task<IEnumerable<ApplicationUser>> GetAllElements()
        {
            http.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue(Constantes.BEARER, Constantes.TOKEN_ADMIN_PROP);
            string stringInput = await http.GetStringAsync(new Uri(Constantes.API_ACCOUNT));
            ApplicationUser[] elements = JsonConvert.DeserializeObject<ApplicationUser[]>(stringInput);

            return elements;
        }

        public async Task DeleteUser(string id)
        {
            try
            {
                http.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue(Constantes.BEARER, Constantes.TOKEN_ADMIN_PROP);
                var stringInput = await http.DeleteAsync(new Uri(Constantes.API_ACCOUNT + id));
            }
            catch(WebException e)
            {
                var messageDialog = new MessageDialog(Constantes.MESSAGE_ERREUR_CONNEXION)
                {
                    Title = Constantes.TITRE_ERREUR_CONNEXION
                };
                var res = await messageDialog.ShowAsync();
            }
        }

        public async Task<int> CountUsers()
        {
            http.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue(Constantes.BEARER, Constantes.TOKEN_ADMIN_PROP);
            string nbUsers = await http.GetStringAsync(new Uri(Constantes.API_COUNTUSERS));
            return JsonConvert.DeserializeObject<int>(nbUsers);
        }

        public async Task<String> GetTokenAdmin(Login admin)
        {
            string token = null;
            HttpResponseMessage reponse = await http.PostAsJsonAsync(Constantes.API_JWT, admin);
            if(reponse.IsSuccessStatusCode)
            {
                TokenResponse responseContent = await reponse.Content.ReadAsAsync<TokenResponse>();
                string tokenTemp = responseContent.Access_Token;
                http.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue(Constantes.BEARER, tokenTemp);
                reponse = await http.GetAsync(Constantes.API_ROLES);

                if(reponse.IsSuccessStatusCode)
                {
                    token = tokenTemp;
                }
            }
            return token;
        }
    }
}
