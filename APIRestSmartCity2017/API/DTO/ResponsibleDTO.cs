using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace APIRestSmartCity2017.DTO
{
    public class ResponsibleDTO
    {
        public decimal RegistrationNumber { get; set; }
        public string Login { get; set; }
        public string Password { get; set; }
        public decimal Age { get; set; }
        public string Sex { get; set; }
        public string PhoneNumber { get; set; }
    }
}
