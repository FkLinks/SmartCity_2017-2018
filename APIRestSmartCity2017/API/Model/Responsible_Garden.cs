using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace APIRestSmartCity2017.Model
{
    public partial class Responsible_Garden
    {
        public decimal Id { get; set; }
        public decimal IdNumGarden { get; set; }
        public Garden NumGarden { get; set; }
        public decimal IdRegistrationNumber { get; set; }
        public Responsible RegistrationNumber { get; set; }

        public Responsible_Garden()
        {

        }
    }
}
