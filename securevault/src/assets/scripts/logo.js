window.addEventListener('load', handleWindowLoad);

function handleWindowLoad() {
    const logo = document.getElementById('logo');
    const login = document.getElementById('login');
    logo.addEventListener('click', LogoClick);
    login.style.visibility = 'hidden';
}

function LogoClick() {
    const login = document.getElementById('login');

    if (login.style.visibility != 'visible'){
        makeVisible();
    } else {
        hide()
    }

}


function makeVisible() {
    const login = document.getElementById('login');
    login.style.visibility = "visible";
}

function hide() {
    const login = document.getElementById('login');
    login.style.visibility = "hidden";
}

