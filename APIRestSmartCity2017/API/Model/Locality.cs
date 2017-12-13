using System;
using System.Collections.Generic;

namespace APIRestSmartCity2017.Model
{
    public partial class Locality
    {
        public decimal Id { get; set; }
        public string PostalCode { get; set; }
        public string NameLocality { get; set; }
        public decimal Country { get; set; }

        public Country CountryNavigation { get; set; }
    }
}
