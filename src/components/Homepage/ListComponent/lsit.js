import './List.css'

var CONTACTS = [
  {
    id:1,
    name: 'Geralt',
    image: 'https://i.pinimg.com/736x/34/42/d7/3442d7bda02f7ca7bf0566304c0c939a.jpg',
    phone: '+41242341287',
    email: 'geraltfromrivia@morhen.kaed',
    adress: "Kaer Morhen, Kaedwen"
  },
  {
    id:2,
    name: 'Dandelion',
    image: 'http://i.playground.ru/i/98/19/20/00/wiki/content/y1rqpmxj.250xauto.png',
    phone: '+46785412354',
    email: 'thegreatestpoet@chameleon.red',
    adress: "Cabaret 'Chameleon', Novigrad, Redania"
  },
  {
    id:3,
    name: 'Yennefer',
    image: 'https://vignette2.wikia.nocookie.net/vedmak/images/c/cd/%D0%99%D0%B5%D0%BD%D0%BD%D0%B8%D1%84%D1%8D%D1%80%D0%923.png/revision/latest/scale-to-width-down/350?cb=20160414164624',
    phone: '+28675674329',
    email: 'yen.ven@aretuza.taned',
    adress: "Vengerberg, Aedirn"
  },
  {
    id:4,
    name: 'Triss',
    image: 'https://vignette.wikia.nocookie.net/vedmak/images/b/bc/%D0%A2%D1%80%D0%B8%D1%81%D1%81%D0%923.png/revision/latest/scale-to-width-down/350?cb=20160422141718',
    phone: '+16578564738',
    email: 'triss.merigold@aretuza.taned',
    adress: "Maribor, Temeria"
  }
]

var Contact = React.createClass({
  getInitialState: function(){
    return {
      isOpened: false
    };
  },
  handleOpen: function(){
    if(this.state.isOpened == true)
    {
      this.setState({
        isOpened:false
      });
    }else{
      this.setState({
        isOpened:true
      });
    }
  },
    render: function() {
      if(this.state.isOpened == true){
        return (
            <li className="contact" onClick={this.handleOpen}>
                <img className="contact-image" src={this.props.image} width="60px" height="60px" />
                <div className="contact-info">
                    <div className="contact-name"> {this.props.name} </div>
                    <div className="info"> {this.props.phoneNumber} </div>
                    <div className="info  fadeIn animated"> {this.props.email} </div>
                    <div className="info  fadeIn animated"> {this.props.adress} </div>
                </div>
            </li>
        );
      } else {
        return (
            <li className="contact" onClick={this.handleOpen}>
                <img className="contact-image" src={this.props.image} width="60px" height="60px" />
                <div className="contact-info">
                <div className="contact-name"> {this.props.name} </div>
                <div className="info"> {this.props.phoneNumber} </div>
                </div>
            </li>
        );
      }

    }
});

var ContactsList = React.createClass({
    getInitialState: function() {
        return {
            displayedContacts: CONTACTS
        };
    },

    handleSearch: function(event) {
        var searchQuery = event.target.value.toLowerCase();
        var displayedContacts = CONTACTS.filter(function(el) {
            var searchValue = el.name.toLowerCase();
            return searchValue.indexOf(searchQuery) !== -1;
        });

        this.setState({
            displayedContacts: displayedContacts
        });
    },

    render: function() {
        return (
            <div className="contacts">
                <input type="text" className="search-field" onChange={this.handleSearch} />
                <ul className="contacts-list">
                    {
                       this.state.displayedContacts.map(function(el) {
                           return <Contact
                               key={el.id}
                               name={el.name}
                               phoneNumber={el.phone}
                               image={el.image}
                               email={el.email}
                               adress={el.adress}
                           />;
                       })
                    }
                </ul>
            </div>
        );
    }
});

  ReactDOM.render(
      <ContactsList/>,
      document.getElementById('content')
  );