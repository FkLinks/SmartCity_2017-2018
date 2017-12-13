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
    class AccountController
    {
        public async Task<IEnumerable<ApplicationUser>> GetAllElements()
        {
            var http = new HttpClient();
            var stringInput = await http.GetStringAsync(new Uri("http://smartcity-jardin-20172018.azurewebsites.net/api/Account"));
            ApplicationUser[] elements = JsonConvert.DeserializeObject<ApplicationUser[]>(stringInput);

            return elements;
        }

        public async Task DeleteUser(string id)
        {
            var http = new HttpClient();
            var stringInput = await http.DeleteAsync(new Uri("http://smartcity-jardin-20172018.azurewebsites.net/api/Account/"+id));
        }
    }
}
