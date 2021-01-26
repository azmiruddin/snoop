
function App (){
    const [data, setData] = useState({
        balances: null,
        address: null,
    });

    useEffect(() => {
        setAppState({ loading: true });
        const apiUrl = `https://api.github.com/users/hacktivist123/repos`;
        fetch(apiUrl)
        .then((res) => res.json())
        .then((repos) => {
            setAppState({ balances: balancesEthAccount, address: listEthAccount });
        });
    }, [setAppState]);
}
