using System;
using System.Collections.Generic;

namespace APIRestSmartCity2017.Model
{
    public partial class Country
    {
        public Country()
        {
            Locality = new HashSet<Locality>();
        }

        public decimal Id { get; set; }
        public string NameCountry { get; set; }

        public ICollection<Locality> Locality { get; set; }
    }
}
