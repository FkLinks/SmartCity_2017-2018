using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace BackOffice_SmartCity.Model
{
    public class Responsable
    {
        public decimal RegistrationNumber { get; set; }
        public string Login { get; set; }
        public string Password { get; set; }
        public decimal Age { get; set; }
        public string Sex { get; set; }
        public string PhoneNumber { get; set; }
    }
}
