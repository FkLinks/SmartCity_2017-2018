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
    public class GardenController
    {
        private HttpClient http = Constantes.GetHttp();

        public async Task<IEnumerable<Jardin>> GetAllElements()
        {
            string stringInput = await http.GetStringAsync(new Uri(Constantes.API_GARDENS));
            Jardin[] elements = JsonConvert.DeserializeObject<Jardin[]>(stringInput);

            return elements;
        }

        public async Task DeleteGarden(decimal? id)
        {
            http.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue(Constantes.BEARER, Constantes.TOKEN_ADMIN_PROP);
            var stringInput = await http.DeleteAsync(new Uri(Constantes.API_GARDENS + id));
        }

        public async Task<String> PostGarden(Jardin jardin)
        {
            http.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue(Constantes.BEARER, Constantes.TOKEN_ADMIN_PROP);
            var stringInput = await http.PostAsJsonAsync(Constantes.API_GARDENS, jardin);
            return stringInput.ReasonPhrase;
        }

        public async Task<String> PutGarden(Jardin jardin)
        {
            http.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue(Constantes.BEARER, Constantes.TOKEN_ADMIN_PROP);
            var stringInput = await http.PutAsJsonAsync(Constantes.API_GARDENS + jardin.NumGarden, jardin);
            return stringInput.ReasonPhrase;
        }

        public async Task<int> CountGardens()
        {
            http.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue(Constantes.BEARER, Constantes.TOKEN_ADMIN_PROP);
            string nbGardens = await http.GetStringAsync(new Uri(Constantes.API_COUNTGARDENS));
            return JsonConvert.DeserializeObject<int>(nbGardens);
        }
    }
}
