/**
 * Account Settings - Account
 */

'use strict';

document.addEventListener('DOMContentLoaded', function (e) {
  (function () {
    const deactivateAcc = document.querySelector('#formAccountDeactivation');

    // Update/reset user image of account page
    let accountUserImage = document.getElementById('uploadedAvatar');

    const fileInput = document.querySelector('.account-file-input'),
      resetFileInput = document.querySelector('.account-image-reset');

    if (accountUserImage) {
      const resetImage = accountUserImage.src;
      fileInput.onchange = () => {
        if (fileInput.files[0]) {
          accountUserImage.src = window.URL.createObjectURL(fileInput.files[0]);
        }
      };
      resetFileInput.onclick = () => {
        fileInput.value = '';
        accountUserImage.src = resetImage;
      };
    }

    const deactivateAcc2 = document.querySelector('#formAccountDeactivation');
    let accountUserImage2 = document.getElementById('uploadedAvatar2');
    const fileInput2 = document.querySelector('.account-file-input'),
        resetFileInput2 = document.querySelector('.account-image-reset');
        if (accountUserImage2) {
          const resetImage = accountUserImage2.src;
          fileInput2.onchange = () => {
            if (fileInput2.files[0]) {
              accountUserImage2.src = window.URL.createObjectURL(fileInput.files[0]);
            }
          };
          resetFileInput.onclick = () => {
            fileInput2.value = '';
            accountUserImage2.src = resetImage;
          };
        }

        let accountUserImage3 = document.getElementById('uploadedAvatar3');
        const fileInput3 = document.querySelector('.account-file-input'),
            resetFileInput3 = document.querySelector('.account-image-reset');
        if (accountUserImage3) {
          const resetImage = accountUserImage3.src;
          fileInput3.onchange = () => {
            if (fileInput2.files[0]) {
              accountUserImage3.src = window.URL.createObjectURL(fileInput.files[0]);
            }
          };
          resetFileInput.onclick = () => {
            fileInput3.value = '';
            accountUserImage3.src = resetImage;
          };
        }




      }
  )();
}



);
