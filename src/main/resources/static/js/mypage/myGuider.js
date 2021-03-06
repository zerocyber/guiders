const body = document.body;
const guiderModal = document.getElementById("modal");
const modalSpan = document.getElementsByClassName("close")[0];
const mtrModal = document.querySelector('#mtr-modal');
const mtrModalBody = document.querySelector('#mtr-modal-body');

window.addEventListener('load', () => {
    fetch('/following')
        .then((res) => res.json())
        .then((guiderList) => {
            guiderList.forEach((guider) => {
                let ques = '';
                if(guider.mentoringList.length){
                    ques = '<p class="question-head">질문 목록</p>';
                    guider.mentoringList.forEach((q) => {
                        ques += '<p class="question" data-mtrno="' + q.id + '">' + q.title + '</p>';
                    });
                } else{
                    ques = '<p class="question-suggestion">질문을 남겨보세요!</>'
                }
                document.querySelector('.title').innerHTML +=
                    `<ul class="follower-content">
            <li>
              <img class="profile-img" src="` + guider.photoUrl + `">
              <div class="guider-name">` + guider.name + ` 가이더</div>
            </li>
            <li>
              <div class="mtr-info">
                <div class="job"><div>근무회사</div> ` + guider.currentJob + `</div>
                <div class="department"><div>부서</div> ` + guider.department + `</div>
                <input type="hidden" class="field "value="` + guider.field + `">
                <input type="hidden" class="lang" value="` + guider.lang + `">
              </div>
            </li>
            <li>
              ${ques}
            </li>
            <li>
              <button class="question-btn">질문하기</button>
              <button class="unfollow-btn">UNFOLLOW</button>
              <div class="more-qna">전체 질문 보기</div>
            </li>
            <input type="hidden" class="guider-id" value="` + guider.id + `">
          </ul>`;
        })
    });
});

document.querySelector('.title').addEventListener("click", ({target}) => {
    switch(target.className){
        case 'profile-img':
            const guiderId = target.parentElement.parentElement.lastElementChild.value;
            fetch(`/guider?email=${guiderId}`)
                .then((res) => res.json())
                .then((guider) => {
                    document.querySelector('.modal-content-title>img').src = guider.photo;
                    document.querySelector('div>strong').innerText = guider.name;
                    document.querySelector('.modal-content-title>div:nth-child(4)>span:nth-child(2)').innerText
                        = guider.currentJob;
                    document.querySelector('.modal-content-title>div:nth-child(5)>span:nth-child(2)').innerText
                        = guider.department;
                    document.querySelector('.modal-content-title>div:nth-child(6)>span:nth-child(2)').innerText
                        = guider.field;
                    document.querySelector('.modal-content-body > div').innerHTML = guider.introduction;
                    guiderModal.style.display = "block";
                })
            break;
        case 'question-btn':
            const hiddenList = target.parentElement.parentElement.querySelectorAll('input[type="hidden"]');
            document.querySelector('#field').innerText = hiddenList[0].value;
            document.querySelector('#lang').innerText = hiddenList[1].value;
            document.querySelector('#guider-id').value = hiddenList[2].value;
            document.querySelector('#mtr-modal-body>h2>span').innerText =
                target.parentElement.parentElement.firstElementChild.children[1].innerText
            mtrModal.style.display = 'block';
            let i = 1;
            const increase = setInterval(function () {
              if (i === 51) {
                clearInterval(increase);
              } else {
                mtrModal.style.backgroundColor = 'rgba(0, 0, 0,' + 0.01 * i + ')';
                i++;
              }
            }, 10);
            break;
        case "more-qna":
            location.href = `/mentoring/list?guiderId=${target.parentElement.parentElement.querySelector('.guider-id').value}`;
    }
    if(target.getAttribute('data-mtrno')){
        location.href = '/mentoring/' + target.getAttribute('data-mtrno');
    }
    
});

document.querySelector('#mtr-submit').addEventListener('click', function({target}) {
    let content = document.querySelector('textarea[name="content"]').value;
    content = content.replace(/(?:\r\n|\r|\n)/g, '<br />');
    const mentoring = {
        guiderId: document.querySelector('#guider-id').value,
        field: document.querySelector('#field').innerText,
        lang: document.querySelector('#lang').innerText,
        title: document.querySelector('input[name="title"]').value,
        content: content,
    }

    fetch('/mentoring', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(mentoring)
    }).then((res) => {
        if (res.status === 200) {
            location.reload();
        }
    })
});

document.querySelector('#mtr-cancel').addEventListener('click', function () {
    let i = 50;
    const decrease = setInterval(function () {
        if (i === -1) {
            mtrModal.style.display = 'none';
            mtrModalBody.style.opacity = 1;
            clearInterval(decrease);
        } else {
            mtrModal.style.backgroundColor = 'rgba(0, 0, 0,' + 0.01 * i + ')';
            mtrModalBody.style.opacity = 0.02 * i;
            i--;
        }
    }, 10);
});
    
window.addEventListener("click", function(){
    if(event.target === guiderModal){
        guiderModal.style.display = "none";
        body.style.overflow = "";
    }
});