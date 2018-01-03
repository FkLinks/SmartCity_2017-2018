using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace APIRestSmartCity2017.Model
{
    public partial class Plant_Garden
    {
        public decimal IdPlant_Garden { get; set; }
        public decimal IdNumGarden { get; set; }
        public Garden NumGarden { get; set; }
        public decimal IdPlant { get; set; }
        public Plant Id { get; set; }

        public Plant_Garden()
        {

        }
    }
}
