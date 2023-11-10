import { Dispatch, SetStateAction } from "react";
import { storage } from "../firebase/firebase";
import { ref, getDownloadURL, uploadBytesResumable, deleteObject } from "firebase/storage";


interface Props {
    file: File | null
    setFileRef: Dispatch<SetStateAction<string>>
    directory: string,
    setProgress?: Dispatch<SetStateAction<number>>,
    cancel: {
        uploadingCancellation: boolean,
        setUploadingCancellation: Dispatch<SetStateAction<boolean>>
    }
}


const uploadFile = ({file, setFileRef, directory, setProgress, cancel}: Props) => {

    if (file == null) return;
    const storageRef = ref(storage, `${directory}/${file.name}`);
    const uploadTask = uploadBytesResumable(storageRef, file);

    uploadTask.on('state_changed',
        (snapshot) => {
            const progress = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;

            if (setProgress !== undefined) {
                setProgress(progress)
            }            
        },
        (error) => {

        },
        () => {

            getDownloadURL(uploadTask.snapshot.ref).then((downloadURL) => {
                setFileRef(downloadURL.toString())
            })
        }
    )
};

export default uploadFile