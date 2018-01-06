using BackOffice_SmartCity.Model;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace BackOffice_SmartCity.Service
{
    public class ResponsibleController
    {
        private HttpClient http = Constantes.GetHttp();

        public async Task<IEnumerable<Responsable>> GetAllElements()
        {
            http.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue(Constantes.BEARER, Constantes.TOKEN_ADMIN_PROP);
            string stringInput = await http.GetStringAsync(new Uri(Constantes.API_RESPONSIBLES));
            Responsable[] elements = JsonConvert.DeserializeObject<Responsable[]>(stringInput);

            return elements;
        }

        public async Task DeleteRespon(decimal id)
        {
            http.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue(Constantes.BEARER, Constantes.TOKEN_ADMIN_PROP);
            var stringInput = await http.DeleteAsync(new Uri(Constantes.API_RESPONSIBLES + id));
        }

        public async Task<String> PutResponsable(Responsable responsable)
        {
            http.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue(Constantes.BEARER, Constantes.TOKEN_ADMIN_PROP);
            var stringInput = await http.PutAsJsonAsync(Constantes.API_RESPONSIBLES + responsable.RegistrationNumber, responsable);
            return stringInput.ReasonPhrase;
        }

        public async Task<String> PostResponsable(Responsable responsable)
        {
            /*---CRYPTAGE DU PASSWORD---*/
            if(responsable.Password != null)
            {
                byte[] passBytes = System.Text.Encoding.Unicode.GetBytes(responsable.Password);
                string encryptPass = Convert.ToBase64String(passBytes);
                responsable.Password = encryptPass;
            }

            http.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue(Constantes.BEARER, Constantes.TOKEN_ADMIN_PROP);
            var stringInput = await http.PostAsJsonAsync(Constantes.API_RESPONSIBLES, responsable);

            return stringInput.ReasonPhrase;
        }

        public async Task<int> CountRespon()
        {
            http.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue(Constantes.BEARER, Constantes.TOKEN_ADMIN_PROP);
            string nbRespon = await http.GetStringAsync(new Uri(Constantes.API_COUNTRESPON));
            return JsonConvert.DeserializeObject<int>(nbRespon);
        }
    }
}
