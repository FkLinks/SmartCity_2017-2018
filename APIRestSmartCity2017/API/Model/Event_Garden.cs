using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace APIRestSmartCity2017.Model
{
    public partial class Event_Garden
    {
        public decimal Id { get; set; }
        public decimal IdEvent { get; set; }
        public Event IdEv { get; set; }
        public decimal IdNumGarden { get; set; }
        public Garden NumGarden { get; set; }

        public Event_Garden()
        {

        }
    }
}
