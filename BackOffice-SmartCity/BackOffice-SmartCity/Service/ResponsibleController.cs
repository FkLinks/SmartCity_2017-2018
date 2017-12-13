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
    class ResponsibleController
    {
        public async Task<IEnumerable<Responsable>> GetAllElements()
        {
            var http = new HttpClient();
            var stringInput = await http.GetStringAsync(new Uri("http://smartcity-jardin-20172018.azurewebsites.net/api/Responsibles"));
            Responsable[] elements = JsonConvert.DeserializeObject<Responsable[]>(stringInput);

            return elements;
        }

        public async Task DeleteRespon(decimal id)
        {
            var http = new HttpClient();
            var stringInput = await http.DeleteAsync(new Uri("http://smartcity-jardin-20172018.azurewebsites.net/api/Responsibles/"+id));
        }

        public async Task PutResponsable(Responsable responsable)
        {
            var http = new HttpClient();
            var stringInput = await http.PutAsJsonAsync("http://smartcity-jardin-20172018.azurewebsites.net/api/Responsibles", responsable);
        }
    }
}
