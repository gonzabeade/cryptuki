

function showPending(offerId){
    showCard(offerId,'PENDING',true);
    showCard(offerId,'ACCEPTED',false);
    showCard(offerId,'REJECTED',false);
    showCard(offerId,'SOLD',false);

    let pendingTab = document.getElementById('tab-'+offerId+'-PENDING')
    pendingTab.classList.remove('text-gray-500')
    pendingTab.classList.remove('bg-nyellow/[0.6]')
    pendingTab.classList.add('bg-nyellow')

    hideAccepted(offerId)
    hideRejected(offerId)
    hideSold(offerId)

}
function showAccepted(offerId){
    showCard(offerId,'PENDING',false);
    showCard(offerId,'ACCEPTED',true);
    showCard(offerId,'REJECTED',false);
    showCard(offerId,'SOLD',false);



    let acceptedTab = document.getElementById('tab-'+offerId+'-ACCEPTED')
    acceptedTab.classList.remove('text-gray-500')
    acceptedTab.classList.remove('bg-ngreen/[0.6]')
    acceptedTab.classList.add('bg-ngreen')

    hidePending(offerId)
    hideRejected(offerId)
    hideSold(offerId)
}
function showRejected(offerId){
    showCard(offerId,'PENDING',false);
    showCard(offerId,'ACCEPTED',false);
    showCard(offerId,'REJECTED',true);
    showCard(offerId,'SOLD',false);

    let rejectedTab = document.getElementById('tab-'+offerId+'-REJECTED')
    rejectedTab.classList.remove('text-gray-500')
    rejectedTab.classList.remove('bg-nred/[0.6]')
    rejectedTab.classList.add('bg-nred')

    hidePending(offerId)
    hideAccepted(offerId)
    hideSold(offerId)

}
function showSold(offerId){
    showCard(offerId,'PENDING',false);
    showCard(offerId,'ACCEPTED',false);
    showCard(offerId,'REJECTED',false);
    showCard(offerId,'SOLD',true);

    let soldTab = document.getElementById('tab-'+offerId+'-SOLD')
    soldTab.classList.remove('text-gray-500')
    soldTab.classList.add('text-white')
    soldTab.classList.remove('bg-gray-500/[0.6]')
    soldTab.classList.add('bg-gray-500')

    hidePending(offerId)
    hideAccepted(offerId)
    hideRejected(offerId)
}

function showCard(offerId, status, active){
    var cards= document.getElementsByName('trade-'+offerId+'-'+status)
    if(cards==null){
       document.getElementById('noResults-'+offerId).classList.remove("hidden");
    }else{
        cards.forEach(
            function (card) {
                if(active){
                    card.classList.remove('hidden')
                }else{
                    card.classList.add('hidden')
                }
            })
    }
    if(active){
        console.log("aa")
        let cardsArray = [...cards]
        var counter = 0;
        cardsArray.forEach(
            function (card){
                if(card.classList.contains('hidden')){
                    counter+=1;
                }
            }
        )
        if(counter === cardsArray.length){
            document.getElementById('noResults-'+offerId).classList.remove("hidden");
        }
        else{
            document.getElementById('noResults-'+offerId).classList.add('hidden');
        }

    }



}

function hideAccepted(offerId) {
    let acceptedTab = document.getElementById('tab-'+offerId+'-ACCEPTED')
    acceptedTab.classList.add('text-gray-500')
    acceptedTab.classList.remove('bg-ngreen')
    acceptedTab.classList.add('bg-ngreen/[0.6]')
}
function hidePending(offerId) {
    let pendingTab = document.getElementById('tab-'+offerId+'-PENDING')
    pendingTab.classList.add('text-gray-500')
    pendingTab.classList.remove('bg-nyellow')
    pendingTab.classList.add('bg-nyellow/[0.6]')
}
function hideSold(offerId) {
    let soldTab = document.getElementById('tab-'+offerId+'-SOLD')
    soldTab.classList.add('text-gray-500')
    soldTab.classList.remove('bg-gray-500')
    soldTab.classList.add('bg-gray-500/[0.6]')
}
function hideRejected(offerId){
    let rejectedTab = document.getElementById('tab-'+offerId+'-REJECTED')
    rejectedTab.classList.add('text-gray-500')
    rejectedTab.classList.remove('bg-nred')
    rejectedTab.classList.add('bg-nred/[0.6]')
}