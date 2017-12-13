using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace BackOffice_SmartCity.Model
{
    public class ApplicationUser
    {
        public string UserName { get; set; }
        public string Password { get; set; }
        public DateTime? Birthdate { get; set; }
        public string Email { get; set; }
        public string Sex { get; set; }
        public string GeographicOrigins { get; set; }
        public string PhoneNumber { get; set; }
        public string Role { get; set; }
    }
}
