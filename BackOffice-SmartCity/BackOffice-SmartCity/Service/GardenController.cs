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
    class GardenController
    {
        public async Task<IEnumerable<Jardin>> GetAllElements()
        {
            var http = new HttpClient();
            var stringInput = await http.GetStringAsync(new Uri("http://smartcity-jardin-20172018.azurewebsites.net/api/Gardens"));
            Jardin[] elements = JsonConvert.DeserializeObject<Jardin[]>(stringInput);

            return elements;
        }

        public async Task DeleteGarden(decimal id)
        {
            var http = new HttpClient();
            var stringInput = await http.DeleteAsync(new Uri("http://smartcity-jardin-20172018.azurewebsites.net/api/Gardens/" + id));
        }

        public async Task PostGarden(Jardin jardin)
        {
            var http = new HttpClient();
            var stringInput = await http.PutAsJsonAsync("http://smartcity-jardin-20172018.azurewebsites.net/api/Gardens", jardin);
        }

        public async Task PutGarden(Jardin jardin)
        {
            var http = new HttpClient();
            var stringInput = await http.PutAsJsonAsync("http://smartcity-jardin-20172018.azurewebsites.net/api/Gardens", jardin);
        }
    }
}
